(ns invetica.test.spec
  "Useful test functions/macros for working with clojure.spec."
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.spec.test.alpha :as stest]
   ;; Do not remove the `clojure.test.check` require or Cider will break!
   ;;
   ;; See https://github.com/clojure-emacs/cider/issues/1841.
   [clojure.test.check]
   [clojure.test :as t :refer [is]]
   [clojure.spec.alpha :as s]))

;; -----------------------------------------------------------------------------
;; Fixtures

(defn check-asserts
  "Convenience function compatible with `clojure.test/use-fixtures`, that will
  enable checking of clojure.spec assertions.

  E.g.

      (clojure.test/use-fixtures :once test.spec/check-asserts)"
  [f]
  (let [before (s/check-asserts?)]
    (try
      (s/check-asserts true)
      (f)
      (finally
        (s/check-asserts before)))))

(defn instrument
  "Convenience function compatible with `clojure.test/use-fixtures`, that will
  instrument a test run.

  E.g.

      (clojure.test/use-fixtures :once test.spec/instrument)"
  [f]
  (try
    (stest/instrument)
    (f)
    (finally
      (stest/unstrument))))

(def once-fixtures
  "Spec-related fixtures compatible with `clojure.test/use-fixtures`. Enables
  checking of assertions and instrumentation of functions."
  (t/join-fixtures [check-asserts instrument]))

;; -----------------------------------------------------------------------------
;; Test helpers

(defmethod t/assert-expr 'conforming?
  [msg form]
  (let [args (rest form)]
    (assert (<= 2 (count args) 3))
    `(let [result# (s/valid? ~@args)]
       (if result#
         (t/do-report {:actual '~form
                       :expected '~form
                       :message ~msg
                       :type :pass})
         (t/do-report {:actual (::s/problems (s/explain-data ~@args))
                       :expected '~form
                       :message ~msg
                       :type :fail})))))

(defmethod t/assert-expr 'well-specified?
  [msg form]
  (let [ns (nth form 1)]
    `(let [result# (stest/check (stest/enumerate-namespace ~ns))]
       (if (-> result# :failure nil?)
         (t/do-report {:actual '~form
                       :expected '~form
                       :message ~msg
                       :type :pass})
         (t/do-report {:actual result#
                       :expected '~form
                       :message ~msg
                       :type :fail})))))

(defn is-well-specified
  "Checks all of the vars in namespace `ns`, and pretty prints any failures."
  [ns]
  (doseq [result (stest/check (stest/enumerate-namespace ns))]
    (is (-> result :failure nil?)
        (with-out-str (pprint result)))))
