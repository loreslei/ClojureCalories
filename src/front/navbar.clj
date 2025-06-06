(ns front.navbar (:require
                        [hiccup.element :refer [link-to]]))


(defn navbar []
  [:div {:class "w-screen fixed top-0 py-2 bg-indigo-500 flex items-center z-20"}
   [:div {:class "flex items-center justify-between flex-wrap w-screen"}

    ;; Logo mobile
    [:div {:class "w-screen flex justify-between items-center lg:hidden"}
     (link-to "#user"
              [:div {:class "h-10 flex items-center ml-4 gap-2"}
               [:img {:class "w-10 rounded-full" :src "/assets/logo.png"}]
               [:p {:class "text-white font-semibold"} "Clojure Calories"]])

     ;; Botão Mobile
     [:button {:id "Menu" :class "cursor-pointer"}
      [:div {:id "IconBars"}
       [:i {:class "fa-solid fa-bars text-white text-lg mr-7 rounded-xl px-3 py-2 hover:bg-indigo-300 transition-all duration-500"}]]
      [:div {:id "IconClose" :class "hidden"}
       [:i {:class "fa-solid fa-xmark text-white text-2xl mr-7 rounded-xl px-3 py-2 hover:bg-indigo-300 transition-all duration-500"}]]]]

    ;; Menu Oculto Mobile
    [:div {:id "MenuOculto"
           :class "hidden w-screen flex flex-col bg-indigo-500 fixed top-14 p-3 lg:hidden"}
     (link-to "#user"
              [:div {:id "close1" :class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer"}
               [:i {:class "fa-solid fa-user text-white"}]
               [:p {:class "text-white"} "Usuário"]])
     (link-to "#calories"
              [:div {:id "close2" :class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer"}
               [:i {:class "fa-solid fa-weight-scale text-white"}]
               [:p {:class "text-white"} "Alimentos & Exercícios"]])
     (link-to "#results"
              [:div {:id "close3" :class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer"}
               [:i {:class "fa-solid fa-check-to-slot text-white"}]
               [:p {:class "text-white"} "Resultados"]])]

    ;; Navbar Desktop
    [:div {:class "hidden w-screen lg:flex justify-between mx-5"}
     (link-to "#user"
              [:div {:class "h-10 flex items-center ml-4 gap-2"}
               [:img {:class "w-10 rounded-full" :src "/assets/logo.png"}]
               [:p {:class "text-white font-semibold"} "Clojure Calories"]])
     (link-to "#user"
              [:div {:class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer"}
               [:i {:class "fa-solid fa-user text-white"}]
               [:p {:class "text-white"} "Usuário"]])
     (link-to "#calories"
              [:div {:class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer"}
               [:i {:class "fa-solid fa-weight-scale text-white"}]
               [:p {:class "text-white"} "Alimentos & Exercícios"]])
     (link-to "#results"
              [:div {:class "flex items-center gap-2 rounded-xl px-6 py-2 hover:bg-indigo-300 cursor-pointer mr-2"}
               [:i {:class "fa-solid fa-check-to-slot text-white"}]
               [:p {:class "text-white"} "Resultados"]])]]])