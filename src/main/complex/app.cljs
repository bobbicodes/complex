(ns complex.app
  (:require 
   [reagent.dom :as rdom]
   ["katex" :as katex]))

(defn tex [s x y]
  [:foreignObject
   {:transform "scale(0.7)"
    :width  200 :height 200
    :x x :y y
    :ref (fn [el]
           (when el
             (try
               (katex/render s el #js{:throwOnError false})
                 (catch :default e
                  (js/console.warn "Unexpected KaTeX error" e)
                  (set! el -innerHTML s)))))}])

(def view-box-width 300)
(def view-box-height 300)

(defn grid [size rows]
  [:g
   (for [x (range 0 (inc size) (/ size rows))]
     ^{:key x}
     [:line {:x1     x :y1     size :x2     x  :y2     0
             :stroke "#ffcc00"
             :stroke-width 1
             :opacity (if (= x (/ size 2)) 1 0.2)}])
   (for [y (range 0 (inc size) (/ size rows))]
     ^{:key y}
     [:line {:x1     0  :y1     y :x2     size  :y2     y
             :stroke "#ffcc00"
             :stroke-width 1
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
   [:text {:transform "scale(0.6) translate(306,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 2))) 10)]
   [:text {:transform "scale(0.6) translate(368,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 4))) 10)]
   [:text {:transform "scale(0.6) translate(432,276)"
           :fill      "#ffcc00"} (/ (.round js/Math (* 10 (* x-scale 6))) 10)]
   [:text {:transform "scale(0.6) translate(266,193)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 2 y-scale))) 10) "i")]
   [:text {:transform "scale(0.6) translate(266,130)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 4 y-scale))) 10) "i")]
   [:text {:transform "scale(0.6) translate(266,68)"
           :fill      "#ffcc00"} (str (/ (.round js/Math (* 10 (/ 6 y-scale))) 10) "i")]])

(defn v [x y a]
  [:g [:path {:d (str "M" (+ 146 x) " " (+ 155.5 y)
                      "c.35-2.1 4.2-5.25 5.25-5.6-1.05-.35-4.9-3.5-5.25-5.6")
              :transform (str "rotate(" a " " (+ x 150) " " (+ y 150) ")")
              :stroke "#61e2ff" :fill "none"}]
   [tex "\\textcolor{lightgreen}{z\\cdot i}" 90 10]
   [tex "\\textcolor{lightgreen}{z=z\\cdot 1}" 260 80]
   [:path {:d (str "M150 150l" x " " y)
           :stroke "#61e2ff" :fill "none"}]])

(def x1 -3)
(def y1 5)
(def x2 2)
(def y2 3)

(defn app []
  [:div#app
   [:div
   [:svg {:width    700
                   :view-box (str "0 0 " view-box-width " " view-box-height)
                   }
   [:g [grid view-box-width 16] 
    [arrows view-box-width]
    [ticks view-box-width 16]
    [units 1 1]
    [v 
     (* x1 25) 
     (* y1 -25) 
     (- -180 (* (/ 180 js/Math.PI) (.atan js/Math (/ y1 x1))))]
    [v 
     (* x2 25) 
     (* y2 -25) 
     (- (* (/ 180 js/Math.PI) (.atan js/Math (/ y2 x2))))]]]]])

(defn render []
  (rdom/render [app]
            (.getElementById js/document "root")))

(defn ^:dev/after-load start []
  (render)
  (js/console.log "start"))

(defn ^:export init []
  (js/console.log "init")
  (start))

(defn ^:dev/before-load stop []
  (js/console.log "stop"))
