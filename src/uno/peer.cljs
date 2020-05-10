(ns uno.peer
  (:require
    [cljs.reader :refer [read-string]]))

(set! *warn-on-infer* true)

(def peer (atom nil))
(def connections (atom {}))

(defn- salt-id [id]
  (str id "-uno"))
(defn- peer-id [^js/conn conn]
  (-> conn .-peer (.replace "-uno" "")))

(defn- listen [^js/peer peer handler]
  (.on peer "connection"
       (fn [^js/conn conn]
         (.on conn "data" #(->> % read-string (handler (peer-id conn)))))))

(defn set-peer! [id handler]
  (reset! peer (js/Peer. (salt-id id) #js {:debug 1}))
  (listen @peer handler))

(defn send-to-connection [^js/conn conn content]
  (.send conn (pr-str content)))

(defn send-to-peer [id content]
  (let [id (salt-id id)
        ^js/peer peer @peer]
    (if-let [conn (@connections id)]
      (send-to-connection conn content)
      (let [^js/conn conn (.connect peer id)]
        (.on conn "open" #(send-to-connection conn content))
        (.on conn "close" #(swap! connections dissoc id))
        (swap! connections assoc id conn)))))
