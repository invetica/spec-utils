(defproject invetica/spec "0.4.0"
  :description "Utilities for working with clojure.spec."
  :url "https://github.com/invetica/spec"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/test.check "0.9.0" :scope "provided"]]
  :aliases {"lint" ["do" ["whitespace-linter"] ["eastwood"]]}
  :profiles
  {:dev {:plugins [[jonase/eastwood "0.2.3"]
                   [listora/whitespace-linter "0.1.0"]]}})
