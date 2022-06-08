(ns bf)

(def input "Greatest language ever!\n++++-+++-++-++[>++++-+++-++-++<-]>.\n")

(def hello "++++++[>++++++++++++<-]>.\n>++++++++++[>++++++++++<-]>+.\n+++++++..+++.>++++[>+++++++++++<-]>.\n<+++[>----<-]>.<<<<<+++[>+++++<-]>.\n>>.+++.------.--------.>>+.\n")
(def fact ">++++++++++>>>+>+[>>>+[-[<<<<<[+<<<<<]>>[[-]>[<<+>+>-]<[>+<-]<[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>+<-[>[-]>>>>+>+<<<<<<-[>+<-]]]]]]]]]]]>[<+>-]+>>>>>]<<<<<[<<<<<]>>>>>>>[>>>>>]++[-<<<<<]>>>>>>-]+>>>>>]<[>++<-]<<<<[<[>+<-]<<<<]>>[->[-]++++++[<++++++++>-]>>>>]<<<<<[<[>+>+<<-]>.<<<<<]>.>>>>]")

;; BF interpreter in clojure, no side effects

(defn make-bf-state [program]
  {:program program
   :pc 0
   :pos 0
   :data {}
   :loops []})

;; This works but there should be a way to write it without recur
(defn skip-loop [state]
  (loop [depth 0
         pc (:pc state)]
    (case (nth (:program state) pc)
      \] (if (zero? depth)
           (assoc state :pc (inc pc))
           (recur (dec depth) (inc pc)))
      \[ (recur (inc depth) (inc pc))
      (recur depth (inc pc)))))

(defn run-one-op [state]
  (let [{pc :pc, pos :pos, data :data, loops :loops} state
        current-byte (get data pos 0)
        state (assoc state :pc (inc pc))
        update-data #(assoc state :data (assoc data pos (mod (% current-byte) 256)))]
    (case (nth (:program state) pc)
     \> (assoc state :pos (inc pos))
     \< (assoc state :pos (dec pos))
     \+ (update-data inc)
     \- (update-data dec)
     \. (do (print (char current-byte)) state)
     \, (println "error! input not handled")
     \[ (if (zero? current-byte)
          (skip-loop state)
          (assoc state :loops (conj loops pc)))
     \] (assoc state :pc (peek loops) :loops (pop loops))
     state)))

(defn run-program [program]
  (->> (make-bf-state program)
       (iterate run-one-op)
       (drop-while #(< (:pc %) (count program)))
       first
       :data))

;; (run-program hello)
;; Hello, World!=> {0 0, 1 87, 2 0, 3 100, 4 0, 5 33}