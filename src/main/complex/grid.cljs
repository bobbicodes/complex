(ns complex.grid)

(defn grid [size rows]
  [:g
   (for [x (range 0 (inc size) (/ size rows))]
     ^{:key x}
     [:line {:x1     x :y1     size :x2     x  :y2     0
             :stroke "#ffcc00" :stroke-width 1
             :opacity (if (= x (/ size 2)) 1 0.2)}])
   (for [y (range 0 (inc size) (/ size rows))]
     ^{:key y}
     [:line {:x1     0  :y1     y :x2     size  :y2     y
             :stroke "#ffcc00" :stroke-width 1
             :opacity (if (= y (/ size 2)) 1 0.22)}])])

(defn arrows [size]
  [:path {:d
          (str "M6.2 " (- (/ size 2) 5.6)
               "c-.35 2.1-4.2 5.25-5.25 5.6 1.05.35 4.9 3.5 5.25 5.6"
               "M" (- size 6) " " (+ 5.6 (/ size 2))
               "c.35-2.1 4.2-5.25 5.25-5.6-1.05-.35-4.9-3.5-5.25-5.6"
               "M" (+ 5.6 (/ size 2)) " 6.2"
               "c-2.1-.35-5.25-4.2-5.6-5.25-.35 1.05-3.5 4.9-5.6 5.25"
               "M" (- (/ size 2) 5.6) " " (- size 6)
               "c2.1.35 5.25 4.2 5.6 5.25.35-1.05 3.5-4.9 5.6-5.25")
          :fill "none"
          :stroke "#ffcc00"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 1}])

(defn ticks [size rows]
  [:g
   (for [x (range 0 (- size (/ size rows)) (/ size rows))]
     ^{:key x}
     [:line {:x1     (+ x (/ size rows))
             :y1     (+ (/ (/ size rows) 3) (/ size 2))
             :x2     (+ x (/ size rows))
             :y2     (- (/ size 2) (/ (/ size rows) 3))
             :stroke "#ffcc00"
             :stroke-width 1}])
   (for [y (range 0 (- size (/ size rows)) (/ size rows))]
     ^{:key y}
     [:line {:x1     (+ (/ (/ size rows) 3) (/ size 2))
             :y1     (+ y (/ size rows))
             :x2     (- (/ size 2) (/ (/ size rows) 3))
             :y2     (+ y (/ size rows))
             :stroke "#ffcc00"
             :stroke-width 1}])])

(defn units [x-scale y-scale]
  [:g
   [:text {:transform "scale(0.6) translate(309,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 2))) 10)]
   [:text {:transform "scale(0.6) translate(370,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 4))) 10)]
   [:text {:transform "scale(0.6) translate(434,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 6))) 10)]
   [:text {:transform "scale(0.6) translate(266,193)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 2 y-scale))) 10) "i")]
   [:text {:transform "scale(0.6) translate(266,130)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 4 y-scale))) 10) "i")]
   [:text {:transform "scale(0.6) translate(266,68)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 6 y-scale))) 10) "i")]])
