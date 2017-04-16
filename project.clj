(defproject invetica/spec "0.1.0"
  :description "Utilities for working with clojure.spec."
  :url "https://github.com/invetica/spec"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha15"]
                 [org.clojure/test.check "0.9.0" :scope "provided"]]
  :aliases {"lint" ["do" ["whitespace-linter"] ["eastwood"]]}
  :profiles
  {:dev {:plugins [[jonase/eastwood "0.2.3"]
                   [listora/whitespace-linter "0.1.0"]]}})
