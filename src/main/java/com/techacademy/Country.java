package com.techacademy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//「getter/setter、toString、hashCode、equals」のメソッドを生成
@Data

// すべてのフィールドを引数に持つコンストラクタを生成
@AllArgsConstructor

// 引数を持たないコンストラクタを生成
@NoArgsConstructor

// エンティティクラス（データベースのテーブルにマッピングするクラス）であることを示す。
@Entity

// 対応するテーブル名を指定
@Table(name="country")

public class Country {

	//主キーであることを示す
    @Id
    private String code;
    private String name;
    private int population;
}