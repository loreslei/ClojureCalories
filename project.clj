(defproject app-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [compojure "1.7.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-defaults "0.3.4"] 
                 [hiccup "1.0.5"]
                 [ring "1.9.6"]
                 [org.clojure/data.json "2.4.0"]
                 [org.apache.commons/commons-text "1.10.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler app-clojure.handler/app}
  :main ^:skip-aot app-clojure.core
  :jvm-opts ["-Dfile.encoding=UTF-8"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
