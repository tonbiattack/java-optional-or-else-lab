# デバッグ記録

## 最初に観測した事実

Optionalに `cached` が存在するのに、フォールバック呼び出し回数が1回になっている。

## 再現手順

コミット `2cbf25b` で `mvn --batch-mode test` を実行すると、`[evidence] value=cached fallbackCalls=1`、`expected: <0> but was: <1>` となる。

## 観測

戻り値は `cached` なので値の選択自体は正しい。しかし、`loadFallback`の副作用だけが不要に発生している。

## 仮説比較

| 仮説 | 実験 | 結果 |
| --- | --- | --- |
| Optionalが存在値を失っている | 戻り値を確認する | `cached` のため棄却 |
| `orElse`の引数が先に評価される | フォールバック呼び出し回数を数える | 採用 |
| フォールバック処理のカウンタが壊れている | 空Optionalで同じ処理を実行する | 空の場合は必要な1回のため棄却 |

## 原因

`orElse(T)` はフォールバック値を引数として受け取るため、メソッド呼び出し前に `loadFallback` が評価される。値が存在する場合にSupplierを呼び出さない `orElseGet` とは契約が異なる。[1]

## 修正

`value.orElse(loadFallback(...))` を `value.orElseGet(() -> loadFallback(...))` へ変更した。修正コミットは `141a956` である。

## 再発防止テスト

元のテストを残し、`value=cached fallbackCalls=0` を確認する。修正後は `Tests run: 1, Failures: 0, Errors: 0`、`BUILD SUCCESS` となる。

## References

[1] [Java SE 21 API — Optional](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
