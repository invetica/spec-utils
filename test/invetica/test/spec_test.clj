(ns invetica.test.spec-test
  (:require
   [clojure.spec.alpha :as s]
   [clojure.test :refer :all]
   [invetica.test.spec :as sut]))

(use-fixtures :once sut/once-fixtures)

(s/def ::int integer?)
(s/def ::str string?)

(s/def ::map
  (s/keys :req [::int ::str]))

(deftest t-specs
  (is (well-specified? 'invetica.test.spec)))

(deftest t-assert-expressions
  (is (conforming? ::map {::int 11 ::str "This is a string"})))
