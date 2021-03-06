(ns uno.model
  (:require-macros
    [uno.util :refer [kz]])
  (:require
    [uno.state :as state]))

(def colors ["red" "green" "blue" "khaki"])
(def cards
  (concat
    (range 1 20)
    ["S" "S2" "R" "R2" "P2" "P4"]))
(def queue-size 10)

(let [pool (for [color colors
                 number cards]
             (kz color number))
      card (rand-nth pool)]
  (state/set-game-state
    {:pool (remove #(= card %) pool)
     :stack [card]}))

(defn pickup! []
  (let [{:keys [pool stack]} (state/get-game-state)
        card (rand-nth pool)]
    (state/set-game-state
      {:pool (remove #(= card %) pool)
       :stack stack})
    (state/conj-hand! card)))

(defn pickup-many! []
  (let [{:keys [pool stack]} (state/get-game-state)
        cards (->> pool shuffle (take 7) set)]
    (state/set-game-state
      {:pool (remove cards pool)
       :stack stack})
    (state/union-hand! cards)))

(defn pickup-table! []
  (let [{:keys [pool stack]} (state/get-game-state)]
    (when (> (count stack) 1)
      (state/set-game-state
        {:pool pool
         :stack (pop stack)})
      (state/conj-hand! (peek stack)))))

(defn put-down! [card]
  (let [{:keys [pool stack]} (state/get-game-state)
        stack (conj stack card)
        [to-discard stack] (split-at (- (count stack) queue-size) stack)]
    (state/set-game-state
      {:pool (concat pool to-discard)
       :stack (vec stack)})
    (state/disj-hand! card)))

(defn restart! []
  (let [{:keys [pool stack]} (state/get-game-state)
        hand (state/get-hand)
        pool (distinct (concat pool stack hand))
        new-start (rand-nth pool)]
    (state/set-game-state
      {:pool (remove #(= new-start %) pool)
       :stack [new-start]})
    (state/empty-hand!)
    (pickup-many!)))

(defn restart-peer! []
  (let [{:keys [pool stack]} (state/get-game-state)
        hand (state/get-hand)]
    (state/set-game-state
      {:pool (concat pool hand)
       :stack stack})
    (state/empty-hand!)
    (pickup-many!)))
