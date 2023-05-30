(ns complex.app
  (:require  [reagent.dom :as rdom]
             ["katex" :as katex]
             [complex.grid :as grid]))

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

(defn v [x y a label]
  [:g 
   [:path {:d (str "M" (+ 146 x) " " (+ 155.5 y)
                       "c.35-2.1 4.2-5.25 5.25-5.6-1.05-.35-4.9-3.5-5.25-5.6")
           :transform (str "rotate(" a " " (+ x 150) " " (+ y 150) ")")
           :stroke "#61e2ff" :fill      "none"}]
   [tex label (+ x 140) (+ y 155)]
   [:path {:d      (str "M150 150l" x " " y)
           :stroke "#61e2ff" :fill   "none"}]])

(def x1 -7)
(def y1 5)
(def x2 7)
(def y2 3)

(defn app []
  [:div#app
   [:svg {:width    700
          :view-box (str "0 0 " view-box-width " " view-box-height)}
    [:g [grid/grid view-box-width 16] 
     [grid/arrows view-box-width]
     [grid/ticks view-box-width 16]
     [grid/units 1 1]
     [v (* x1 18.5) (* y1 -19) 
      (- -180 (* (/ 180 js/Math.PI) (.atan js/Math (/ y1 x1))))
      "\\textcolor{pink}{z\\cdot i}"]
     [v (* x2 18.5) (* y2 -18.5) 
      (- (* (/ 180 js/Math.PI) (.atan js/Math (/ y2 x2))))
      "\\textcolor{lightgreen}{z=z\\cdot 1}"]]]])

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
