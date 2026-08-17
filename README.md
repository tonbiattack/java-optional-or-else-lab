# Optional.orElseが存在値でもフォールバックを実行する

本ラボは、Optionalに値が存在しても `orElse(T)` の引数が先に評価されるため、不要なフォールバック処理が実行される問題を再現します。

## 実行

```bash
mvn --batch-mode test
```

バグコミットは `2cbf25b`、修正コミットは `141a956` です。修正後は `orElseGet(Supplier)` でフォールバックを遅延評価します。

## 学習の流れ

| 段階 | 観測 |
| --- | --- |
| 再現 | `value=cached fallbackCalls=1` |
| 仮説 | Optionalが値を無視している |
| 切り分け | フォールバック引数の評価タイミングを観測 |
| 修正 | `orElseGet`でSupplierを遅延評価 |

## References

[1] [Java SE 21 API — Optional](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
