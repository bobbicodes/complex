(ns complex.app
  (:require [reagent.dom :as rdom]))

(def view-box-width 300)
(def view-box-height 300)

(defn make-path [points]
  (str "M" (apply str (interpose " " (for [[x y] points]
                                       (str x " " y))))))

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

(defn app []
  [:div#app
   [:h1 "complex"]
   [:div
   [:svg {:width    700
                   :view-box (str "0 0 " view-box-width " " view-box-height)
                   }
   [grid view-box-width 16]]]])

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
