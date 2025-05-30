(ns front.front-end
  (:require [hiccup.page :refer [html5 include-css]]
            [front.navbar :refer [navbar]]
            [front.user-section :refer [user-section]]
            [front.calories-section :refer [calories-section]]
            [front.results-section :refer [results-section]]
            ))
            

(defn home-page []
  (html5 {:class "scroll-smooth"}
   {:lang "pt-br"}
   [:head
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title "Clojure Calories"]
    (include-css "https://fonts.googleapis.com/css2?family=Montserrat&display=swap")
    [:script {:src "https://cdn.tailwindcss.com"}]
    [:script {:src "https://kit.fontawesome.com/16fe6351cd.js" :crossorigin "anonymous"}]
    [:link {:rel "icon" :href "/assets/logo.png"}]
    [:style "body { font-family: 'Montserrat', sans-serif !important; }"]]
   [:body.bg-slate-100.overflow-x-hidden
    (navbar)
    (user-section)
    (calories-section)
    (results-section)
    [:script "
        const toggleElements = () => {
            document.getElementById('MenuOculto').classList.toggle('hidden');
            document.getElementById('IconBars').classList.toggle('hidden');
            document.getElementById('IconClose').classList.toggle('hidden');
        };
        document.getElementById('Menu').onclick = toggleElements;
        ['close1', 'close2', 'close3'].forEach(id => {
            document.getElementById(id).onclick = toggleElements;
        });
    "]]))
