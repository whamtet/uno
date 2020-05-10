(ns uno.msg
  (:require
    [uno.peer :as peer]
    [uno.state :as state]))

(defn handle-incoming [peer [command data]]
  (case command
    (prn peer command data)))

(defn set-peer! [username]
  (peer/set-peer! username handle-incoming))

(defn request-state [other]
  (peer/send-to-peer other [:request-state]))
