# どうぶつ将棋
5 × 5の盤面で行う将棋です．CPUと対戦する形式です．最終的に相手のライオンの駒を取ることが出来たら勝ち，取られたら負けです．

## あそびかた
1. スタート画面で「Easy」，「Normal」，「Hard」のレベルが表示されます．いずれかを選んでボタンをクリックします．
1. いくつかの駒が配置された盤面が表示されます．手前側を向いている駒を操作できます．駒はライオン，キリン，ゾウ，ヒヨコ，シカの5種類です．それぞれの駒は，次のように動けます．
    - ライオン・・・隣接する任意のマスに進むことができます．
    - キリン・・・上下左右のいずれかのマスに進むことができます．
    - ゾウ・・・斜め4マスのいずれかに進むことができます．
    - ヒヨコ・・・前に1マス進むことができます．相手陣地の一段目にたどり着くと裏返りにわとりとなります．にわとりは，斜め後ろ以外の6マスのいずれかに進むことができます．
    - シカ・・・前か斜め後ろのいずれかに進むことができます．

    動かしたい駒をクリックすると動けるマスが黄色で縁取られるので，その中から選択してください．
1. いずれかの駒を動かした先に相手の駒があるとき，相手の駒が盤面外の手元に置かれます．その駒は，任意のタイミングで自分の駒として好きな場所に置いて動かすことができます．
1. 自分が相手のライオンを取るか，相手が自分のライオンを取るとゲームが終了し，スタート画面に戻ります．

## CPUのアルゴリズムについて
以下，あなたを`Player`，CPUを`Enemy`と表現します．また，すべての盤面(手駒，次に駒を動かす人の情報も含む)の集合を $G$ と呼ぶことにします．スコア関数: $score: G \to \mathbb{Z}$ を`Enemy`が有利なら値が大きく，`Player`が有利なら値が小さくなるように定義します(これを上手く定義することにより，CPUは強くなります)．
`Enemy`はスコア関数が大きくなるように．`Player`はスコア関数が小さくなるように動きます．
また， $neighborhood: G \to 2^G$ を， $neighborhood(g) = g から一手進んだときのすべての盤面の集合$ と定義します( $g \in G$ )．
シンプルなCPUのアルゴリズムとして，「`Player`が指した後の盤面 $g$ に対して， $neighborhood(g)$ のうちでスコアが最大となるような手を選ぶ」というものが考えられます．しかしこのアルゴリズムは次の一手のみしか考慮されないという問題があります．

今回のこのアプリでは，chokudaiサーチを用いて次の手を求めました．`Player`が指した後の盤面 $g$ に対して，次の手を求めることを考えます．
まず，探索するターン数 $n$ を決定します．今回は $n = 5$ としました．次に， $n$ 個のPriorityQueue[^pq]を用意します．盤面を要素とし，スコア最大の盤面が $.pop()$ で取り出せるようにします．
1つ目のPrioritQueueに $neighborhood(g)$ をすべて挿入します(以降， $i$ 番目のPriorityQueueを $\text{PriorityQueue}[i]$ と表します)．その後，以下を設定した時間いっぱい繰り返します．今回は0.5秒としました．
- for $i$ in $1, \ldots, n - 1$
    - $state \gets \text{PriorityQueue}[i].pop()$ (ここで， $state$ は`Enemy`が指し終わった直後の盤面です)
    - $next \gets neighborhood(state)の要素の中で，最もscoreが低いもの$ (`Player`はスコアが最小になるように動きます． $next$ は`Player`が指し終わった直後の盤面です)
    - $\text{PriorityQueue}[i + 1]$ に $neighborhood(next)$ をすべて挿入します
    
これが終了したら， $\text{PriorityQueue}[n].pop()$ の盤面となるために必要な最初の一手を次の手として決定します．
この手法を用いることにより，盤面を拡張したとしても必ず次の手を0.5秒以内に確定することができます．

[^pq]: 優先度付きキューと呼ばれるデータ構造で，「それまでに追加した要素のうち，最も大きいものを取り出す」という処理を行うことができます．この説明では， $.pop()$ で最大の要素を返し，その要素を削除するという挙動をするものとします．

## コンパイル/実行
```
javac -cp src src/App.java
```
```
java -cp src App
```
