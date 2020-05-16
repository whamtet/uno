(ns uno.msg
  (:require
    [uno.peer :as peer]
    [uno.model :as model]
    [uno.render :as render]
    [uno.state :as state]))

(def ^:private parent (atom nil))
(def ^:private children (atom #{}))

(defn notify-peers
  ([] (notify-peers :receive-state))
  ([command]
   (doseq [peer (conj @children @parent) :when peer]
     (peer/send-to-peer peer [command (state/get-game-state-msg)]))))

(defn- handle-incoming [peer [command data]]
  (case command
    :request-state
    (do
      (swap! children conj peer)
      (peer/send-to-peer peer [:new-game (state/get-game-state-msg)]))
    :new-game
    (do
      (reset! parent peer)
      (state/set-game-state data)
      (model/pickup-many!)
      (peer/send-to-peer peer [:receive-state (state/get-game-state-msg)])
      (render/render-html))
    :receive-state
    (do
      (state/set-game-state data)
      ; forward the information
      (doseq [child @children :when (not= child peer)]
        (peer/send-to-peer child [:request-state data]))
      (render/render-html))
    :restart
    (do
      (state/set-game-state data)
      (model/restart-peer!)
      (notify-peers)
      (render/render-html))
    (prn peer command data)))

(defn set-peer! [username]
  (peer/set-peer! username handle-incoming))

(defn request-state [other]
  (peer/send-to-peer other [:request-state]))
