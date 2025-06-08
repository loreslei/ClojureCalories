(ns front.calories-section
  (:require [hiccup.form :refer [form-to]]))

(defn calories-section []
  [:div {:id "calories"
         :class "flex h-screen mt-20 w-screen items-center justify-center"}
   [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}
    [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
     "Registrar Alimentos ou Exercícios"]
    [:div {:class "my-5"}
     [:div {:class "flex justify-center mt-2 gap-3"}
      [:label {:id "foodLabel" :for "toggle" :class "text-sm text-gray-700 font-bold"} "Alimento"]
      [:div {:class "relative inline-block w-10 align-middle select-none"}
       [:input {:id "Switch" :type "checkbox" :name "toggle" :class "toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 border-gray-300 appearance-none cursor-pointer"}]
       [:label {:for "toggle" :class "toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"}]]
      [:label {:id "exerciseLabel" :for "toggle" :class "text-sm text-gray-700"} "Exercício"]]

     ;; Seção de Alimento
     (form-to [:post "/registrar/alimento" {:id "formAlimento"}]
              [:div {:id "Food" :class "flex flex-col"}
               [:div {:class "flex items-center gap-2"}
                [:p {:class "p-2 text-slate-600 font-semibold"} "Alimento"]
                [:div {:class "relative group cursor-pointer"}
                 [:i {:class "fa-solid fa-circle-info text-slate-600 transition-all duration-200 hover:text-indigo-500"}]
                 [:div {:class "absolute left-1/2 -translate-x-1/2 top-full mt-2 hidden group-hover:block w-64 bg-gray-800 text-white text-sm p-3 rounded-lg shadow-lg z-10"}
                  "Escreva no formato:"
                  [:br]
                  [:span {:class "text-indigo-300 font-medium"} "Nome do alimento + quantidade (gramas)"]
                  [:br] [:br]
                  [:span {:class "block"} "Exemplos:"]
                  [:div {:class "mt-1 ml-2"}
                   "• Milkshake 100g"
                   [:br]
                   "• Brigadeiro 250g"]]]]

               [:div {:class "flex items-center"}
                [:div {:class "h-10 w-11 rounded-l-xl border-y-1 border-l-1 border-slate-300 p-2 flex items-center justify-center"}
                 [:i {:class "fa-solid fa-burger text-slate-700"}]]
                [:input {:name "alimento" :class "h-10 w-full rounded-r-xl border-1 border-slate-300 p-2 text-slate-600 focus:outline-indigo-600" :placeholder "Nome do Alimento e Porção"}]]
               [:p {:class "p-2 text-slate-600 font-semibold mt-3"} "Data"]
               [:div {:class "flex items-center"}
                [:div {:class "h-10 w-11 rounded-l-xl border-y-1 border-l-1 border-slate-300 p-2 flex items-center justify-center"}
                 [:i {:class "fa-solid fa-calendar-week text-slate-700"}]]
                [:input {:type "date" :name "data" :class "h-10 w-full rounded-r-xl border-1 border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"}]]
               [:button {:id "caloriesFood" :type "submit" :class "w-full transition-all mt-8 h-10 cursor-pointer rounded-xl bg-indigo-500 p-2 font-semibold text-white duration-700 hover:bg-indigo-600"}
                "Registrar Alimento"]])

     ;; Seção de Exercício
     (form-to [:post "/registrar/exercicio" {:id "formExercicio" :class "hidden"}]
              [:div {:id "Exercise" :class "hidden flex flex-col"}
               [:div {:class "flex items-center gap-2"}
                [:p {:class "p-2 text-slate-600 font-semibold"} "Exercício"]
                [:div {:class "relative group cursor-pointer"}
                 [:i {:class "fa-solid fa-circle-info text-slate-600 transition-all duration-200 hover:text-indigo-500"}]
                 [:div {:class "absolute left-1/2 -translate-x-1/2 top-full mt-2 hidden group-hover:block w-64 bg-gray-800 text-white text-sm p-3 rounded-lg shadow-lg z-10"}
                  "Escreva no formato:"
                  [:br]
                  [:span {:class "text-indigo-300 font-medium"} "Nome do exercício + duração (minutos)"]
                  [:br] [:br]
                  [:span {:class "block"} "Exemplos:"]
                  [:div {:class "mt-1 ml-2"}
                   "• Corrida 60 min"
                   [:br]
                   "• Futebol 60 min"]]]]

               [:div {:class "flex items-center"}
                [:div {:class "h-10 w-11 rounded-l-xl border-y-1 border-l-1 border-slate-300 p-2 flex items-center justify-center"}
                 [:i {:class "fa-solid fa-dumbbell text-slate-700"}]]
                [:input {:name "exercicio" :class "h-10 w-full rounded-r-xl border-1 border-slate-300 p-2 text-slate-600 focus:outline-indigo-600" :placeholder "Tipo de Exercício e Duração"}]]
               [:p {:class "p-2 text-slate-600 font-semibold mt-3"} "Data"]
               [:div {:class "flex items-center"}
                [:div {:class "h-10 w-11 rounded-l-xl border-y-1 border-l-1 border-slate-300 p-2 flex items-center justify-center"}
                 [:i {:class "fa-solid fa-calendar-week text-slate-700"}]]
                [:input {:type "date" :name "data" :class "h-10 w-full rounded-r-xl border-1 border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"}]]
               [:button {:id "caloriesExercises" :type "submit" :class "calories w-full transition-all mt-8 h-10 cursor-pointer rounded-xl bg-indigo-500 p-2 font-semibold text-white duration-700 hover:bg-indigo-600"}
                "Registrar Exercício"]])]]])
