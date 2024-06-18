package com.techacademy;

import java.util.List;
import java.util.Optional; // 追加

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 追加

@Service
public class CountryService {
    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    // 全件を検索して返す
    public List<Country> getCountryList() {
        // リポジトリのfindAllメソッドを呼び出す
        return repository.findAll();
    }

    // 1件を検索して返す
    public Country getCountry(String code) {
        // findById() メソッドを使い、レコードを1件検索しています。
        Optional<Country> option = repository.findById(code);
        // Optional はJavaの構文で、orElse(null) で取得できなかった場合に null を返すようにしている
        Country country = option.orElse(null);
        return country;
    }

    // 更新（追加）を行なう
    // @Transactional アノテーションはこのクラスのすべてのメソッドをトランザクション管理対象に設定します。
    //トランザクションとは一連のデータベース操作が失敗した場合、
    //操作前の状態に巻き戻すことでデータベースの整合性を担保する仕組みです。
    //トランザクション管理対象のメソッドは、メソッド呼び出し時に自動的にトランザクションが開始され、
    //そのメソッド終了時に自動的にトランザクションが終了します。
    @Transactional
    public void updateCountry(String code, String name, int population) {
        Country country = new Country(code, name, population);
        repository.save(country);
    }

    // 削除を行なう
    //こちらもトランザクションを使用しています。 deleteById() メソッドを使用してレコードを1件削除しています。
    @Transactional
    public void deleteCountry(String code) {
        repository.deleteById(code);
    }

}