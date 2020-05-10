(ns uno.msg)

(set! *warn-on-infer* true)

(def peer (atom nil))

(defn- salt-id [id]
  (str id "-uno"))

(defn- listen [^js/peer peer]
  (.on peer "connection"
       (fn [^js/conn conn]
         (.on conn "data" prn))))

(defn set-peer! [id]
  (reset! peer (js/Peer. (salt-id id) #js {:debug 1}))
  (listen @peer))
