;;;  Department of Informatics and Computers, UniWA
;;;  Laboratory of AI - Expert Systems with CLIPS
;;;===========================================================
;;;  The problem of the evacuation
;;;
;;;  The simple version to be completed
;;;    Just add the code at the "FILL ME" spaces and then
;;;    load buffer, reset and run step by step.
;;;===========================================================


;;;    FACTS

(deffacts ini       "initial facts"
    (capacity 5)
    (elevator is_at 0 has 0)
    (floor 1 has 2)
    (floor 2 has 6)
    (floor 3 has 4)
)

;;; GENERAL RULES


(defrule end_of_evacuation        "building evacuated"
    (elevator is_at 0 has 0) ; Το ασανσέρ είναι στο ισόγειο με 0 επιβάτες
    ; Όλοι οι όροφοι είναι άδειοι
    (floor 1 has 0)
    (floor 2 has 0)
    (floor 3 has 0)
   =>
   (printout t "END OF EVACUATION")
   (halt)
)



;;; GO_TO BASE FLOOR RULES

(defrule go_to_0_A      "elevator is full"
      (declare (salience 10))
 ?i<- (elevator is_at ? has 5) ;Το ασανσέρ είναι σε κάποιον όροφο γεμάτο
   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0)));Το ασανσέρ είναι στο ισόγειο και άδειο


(defrule go_to_base_B      "no_more people to take"
      (declare (salience 8))
 ?i<- (elevator is_at ? has ?y);Το ασανσέρ είναι σε κάποιον όροφο με y αριθμό επιβατών
      (test (> ?y 0) )
      ;Όλοι οι όροφοι είναι άδειοι
      (floor 1 has 0)
      (floor 2 has 0)
      (floor 3 has 0)

   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0)));Το ασανσέρ είναι στο ισόγειο και άδειο

(defrule move_to_1          "elevator moves to 1st floor "
 ?i<- (elevator is_at ?x has ?y);Το ασανσέρ είναι στον χ όροφο με y αριθμό επιβατών
      (test (not (= ?x 1)));Τσεκάρουμε να μην είναι στο 1ο όροφο ήδη
      (floor 1 has ?n)
       (test (not (= ?n 0)));Τσεκάρουμε να μην είναι άδειος ο όροφος
   =>
      (retract ?i)
      (assert (elevator is_at 1 has ?y)));Το ασανσέρ παει στον 1ο όροφο με y αριθμό επιβατών

(defrule move_to_2          "elevator moves to 2nd floor "
 ?i<- (elevator is_at ?x has ?y)
      (test (not (= ?x 2)))
      (floor 2 has ?n)
      (test (not (= ?n 0)))
   =>
      (retract ?i)
      (assert (elevator is_at 2 has ?y)))




(defrule move_to_3          "elevator moves to 3rd floor "
 ?i<- (elevator is_at ?x has ?y)
      (test (not (= ?x 3)))
      (floor 3 has ?n)
       (test (not (= ?n 0)))
   =>
      (retract ?i)
      (assert (elevator is_at 3 has ?y)))


;;; RULES FOR entering lift

(defrule enter_from_1
              "people at 1st floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at 1 has ?p);Το ασανσέρ είναι στον 1ο όροφο και έχει p αριθμό επιβατών
 ?j<- (floor 1 has ?r);Ο πρώτος όροφος έχει r ανθρώπους
      (test (> ?r 0));Τσεκάρουμε το r να είναι μεγαλύτερο του 0
        (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ ?p ?r)) 0)
          then
          (assert (elevator is_at 1 has (+ ?p ?r));Το ασανσέρ είναι στο 1ο όροφο με p+r επιβάτες
                  (floor 1 has 0));Ο πρώτος όροφος είναι άδειος
          else
          (assert (elevator is_at 1 has ?c);Το ασανσέρ είναι στο 1ο όροφο με c επιβάτες
                  (floor 1 has (- ?r (- ?c  ?p))))));Ο πρώτος όροφος έχει c-(p+r) επιβάτες

(defrule enter_from_2
              "people at 2nd floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at 2 has ?p)
 ?j<- (floor 2 has ?r)
      (test (> ?r 0))
        (capacity ?c)
   =>
      (retract ?i ?j)
      (if  (>= (- ?c (+ ?p ?r)) 0)
          then
          (assert (elevator is_at 2 has (+ ?p ?r))
                  (floor 2 has 0))
          else
          (assert (elevator is_at 2 has ?c)
                  (floor 2 has (- ?r (- ?c  ?p))))))



(defrule enter_from_3
              "people at 3rd floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at "FILL ME" has ?p)
 ?j<- (floor 3 has ?r)
      (test (> ?r 0))
        (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ ?p ?r)) 0)
          then
          (assert (elevator is_at 3 has (+ ?p ?r))
                  (floor 3 has 0))
          else
          (assert (elevator is_at 3 has ?c)
                  (floor 3 has (- ?r (- ?c  ?p))))))
