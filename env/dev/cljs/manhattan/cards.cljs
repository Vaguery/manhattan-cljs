(ns manhattan.cards
  (:require [reagent.core :as reagent :refer [atom]]
            [manhattan.core :as core]
            [devcards.core :as dc]
            [clojure.math.combinatorics :as combo])
  (:require-macros
   [devcards.core
    :as dc
    :refer [defcard defcard-doc defcard-rg deftest]]
    [cljs.test :refer [is testing async]]))

(defcard-rg first-card
  [:div>h1 "This is your first devcard!"])

(defn square-coordinates
  [edge-size]
  (into []
    (for [x (range edge-size)
          y (range edge-size)]
      [x y])
      ))

(deftest square-generators
  (testing "produces coordinate vectors"
    (is (= (square-coordinates 2) [[0 0] [0 1] [1 0] [1 1]]))
    ))

(defn manhattan-distance
  [p1 p2]
  (+ (js/Math.abs (- (first p1) (first p2)))
     (js/Math.abs (- (second p1) (second p2)))
     ))

(deftest manhattan-test
  (testing "manhattan-distance function"
  (is (= 2 (manhattan-distance [0 0] [1 1])))
  (is (= 0 (manhattan-distance [0 1] [0 1])))
  (is (= 7 (manhattan-distance [3 0] [0 -4])))
  ))

(defn all-distances
  [points]
  (map
    #(apply manhattan-distance %)
    (combo/combinations points 2)))

(deftest distances-test
  (testing
    (is (= [1 1 2 2 1 1] (all-distances (square-coordinates 2))))
    (is (= 36 (count (all-distances (square-coordinates 3)))))
    (is (= 4950 (count (all-distances (square-coordinates 10)))))
    (is (= 18 (apply max (all-distances (square-coordinates 10)))))
    (is (= 1 (apply min (all-distances (square-coordinates 10)))))
    ))





(reagent/render [:div] (.getElementById js/document "app"))

;; remember to run 'lein figwheel devcards' and then browse to
;; http://localhost:3449/cards
