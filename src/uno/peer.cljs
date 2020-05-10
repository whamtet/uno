(ns uno.peer
  (:require
    [cljs.reader :refer [read-string]]))

(set! *warn-on-infer* true)

(def peer (atom nil))
(def connections (atom {}))

(defn- listen [^js/peer peer handler]
  (.on peer "connection"
       (fn [^js/conn conn]
         (.on conn "data" #(->> % read-string (handler (.-peer conn)))))))

(defn set-peer! [id handler]
  (reset! peer
          (if id
            (js/Peer. id #js {:debug 1})
            (js/Peer. #js {:debug 1})))
  (listen @peer handler))

(defn send-to-connection [^js/conn conn content]
  (.send conn (pr-str content)))

(defn send-to-peer [id content]
  (if-let [conn (@connections id)]
    (send-to-connection conn content)
    (let [^js/peer peer @peer
          ^js/conn conn (.connect peer id)]
      (.on conn "open" #(send-to-connection conn content))
      (.on conn "close" #(swap! connections dissoc id))
      (swap! connections assoc id conn))))
