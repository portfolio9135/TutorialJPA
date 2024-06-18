package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("country")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }



    // ----- 一覧画面 -----
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("countrylist", service.getCountryList());
        // country/list.htmlに画面遷移
        return "country/list";
    }



    // ----- 詳細画面 -----
    //詳細画面は「新規登録」と「更新」の2つの画面を兼ねています。そこで @GetMapping(value = { "/detail", "/detail/{code}/" }) で2つのパスにマッピングしています。

    //codeが無い場合（新規登録）: country/detail
    //codeがある場合（更新）: country/detail/{code}

    //codeが指定されていたら、サービスの getCountry() メソッドで1件検索して結果を取得します。
    //codeが指定されていない場合は、空の Country オブジェクトを作成します。必ず Country オブジェクトをModelに登録することで、
    //detail.html テンプレートの方でエラーになることを防いでいます。

    @GetMapping(value = { "/detail", "/detail/{code}/" })
    public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
        // codeが指定されていたら検索結果、無ければ空のクラスを設定
        Country country = code != null ? service.getCountry(code) : new Country();
        // Modelに登録
        model.addAttribute("country", country);
        // country/detail.htmlに画面遷移
        return "country/detail";
    }



    // ----- 更新（追加） -----
    //画面のformで入力した値をパラメータとして受け取り、サービスの updateCountry() メソッドで更新処理を行ないます。
    //codeの値が同じである（＝すでに登録されている）レコードがない場合には追加されます。
    //更新処理が終わったら一覧画面にリダイレクトします。リダイレクトさせるには "redirect:テンプレート名" と記述します。

    @PostMapping("/detail")
    public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("population") int population, Model model) {
        // 更新（追加）
        service.updateCountry(code, name, population);

        // 一覧画面にリダイレクト
        return "redirect:/country/list";
    }



    // ----- 削除画面 -----
    //削除画面用のコントローラーは、削除画面に遷移する時に、削除する国のコードをモデルに渡すようにする
    //画面のformで入力したcodeをパラメータとして受け取り、
    //サービスの deleteCountry() メソッドで削除処理を行ないます。更新処理が終わったら一覧画面にリダイレクトします。

    @GetMapping("/delete/{code}")
    public String deleteCountryForm(@PathVariable String code, Model model) {
        // モデルにcodeを追加
        model.addAttribute("code", code);
        // country/delete.htmlに画面遷移
        return "country/delete";
    }


    // ----- 削除 -----
    @PostMapping("/delete")
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        // 削除
        service.deleteCountry(code);

        // 一覧画面にリダイレクト
        return "redirect:/country/list";
    }

}