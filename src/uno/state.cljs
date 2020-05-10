(ns uno.state
  (:require-macros
    [uno.util :as util]))

(def ^:private username (atom nil))
(util/setget username)

(def ^:private game-state (atom nil))
(util/setget game-state)
