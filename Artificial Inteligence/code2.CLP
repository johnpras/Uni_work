;Αρχικοποίηση των facts τα οποία θα τα εισάγει ο χρήστης

(deffacts ini       "initial facts"
    (elevator is_at 0 has 0 Adults and 0 Minors)
    (capacity (read))
    (floor 1 has (read) Adults and (read) Minors)
    (floor 2 has (read) Adults and (read) Minors)
    (floor 3 has (read) Adults and (read) Minors)
)

;Κανόνας "ΤΕΛΟΥΣ"/η σωτηρία μας
;Αν το ασανσέρ είναι στο 0 και δεν έχει επιβάτες
;Αν όλοι οι όροφοι είναι άδειοι τότε φτάσαμε στο στόχο μας και τελειώνουμε το πρόγραμμα
(defrule end_of_evacuation        "building evacuated"
    (elevator is_at 0 has 0 Adults and 0 Minors)
    (floor 1 has 0 Adults and 0 Minors)
    (floor 2 has 0 Adults and 0 Minors)
    (floor 3 has 0 Adults and 0 Minors)
   =>
   (printout t "END OF EVACUATION")
   (halt)
)

;Επιστροφή του ασανσέρ στο 0 για να αδειάσει αν επιβάτες == capacity
(defrule go_to_0_A      "elevator is full"
      (declare (salience 10))
      (capacity ?c)
 ?i<- (elevator is_at ? has ?A Adults and ?M Minors)
      (test (= (+ ?A ?M) ?c))
   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0 Adults and 0 Minors))
)

;Επιστροφή του ασανσέρ στο 0 γιατί δεν υπάρχουν άλλοι ένοικοι στους ορόφους
(defrule go_to_0_B      "no_more people to take"
      (declare (salience 8))
 ?i<- (elevator is_at ? has ?A Adults and ?M Minors)
      (floor 1 has 0 Adults and 0 Minors)
      (floor 2 has 0 Adults and 0 Minors)
      (floor 3 has 0 Adults and 0 Minors)
   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0 Adults and 0 Minors))
      (printout t "END OF EVACUATION")
      (halt)
)

;Κανόνες μετακίνησης στους ορόφους

;Αν υπάρχουν άτομα στο 1ο όροφο αφαιρείται το fact της κατάστασης του ασανσέρ και προσθέτουμε ένα νέο fact με το ασανσέρ στο 1ο όροφο και με τους επιβάτες που είχε.
(defrule move_to_1         "elevator moves to 1st floor "
 ?i<- (elevator is_at ?f has ?A Adults and ?M Minors)
      (test (not (= ?f 1)))
      (floor 1 has ?n Adults and ?n2 Minors )
      (or (test (not (= ?n 0)))
          (test (not (= ?n2 0)))
      )
   =>
      (retract ?i)
      (assert (elevator is_at 1 has ?A Adults and ?M Minors))
)

(defrule move_to_2          "elevator moves to 2nd floor "
 ?i<- (elevator is_at ?f has ?A Adults and ?M Minors)
      (test (not (= ?f 2)))
      (floor 2 has ?n Adults and ?n2 Minors )
      (or (test (not (= ?n 0)))
          (test (not (= ?n2 0)))
      )
   =>
      (retract ?i)
      (assert (elevator is_at 2 has ?A Adults and ?M Minors))
)

(defrule move_to_3          "elevator moves to 3rd floor "
 ?i<- (elevator is_at ?f has ?A Adults and ?M Minors)
      (test (not (= ?f 3)))
      (floor 3 has ?n Adults and ?n2 Minors )
      (or (test (not (= ?n 0)))
          (test (not (= ?n2 0)))
      )
   =>
      (retract ?i)
      (assert (elevator is_at 3 has ?A Adults and ?M Minors))
)

;Κανόνες επιβίβασης στο ασανσέρ
(defrule enter_from_1
              "people at 1st floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at 1 has ?A Adults and ?M Minors)
 ?j<- (floor 1 has ?n Adults and ?n2 Minors )
      (or (test (> ?n 0)) ;έλεγχος για το αν υπάρχουν άτομα στον όροφο
          (test (> ?n2 0))
      )
      (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ (+ ?A ?M) (+ ?n ?n2))) 0) ;έλεγχος για το αν χωράνε όλοι μέσα
          then ;αν ναι απλά βάζουμε ολους τους ενοίκους μέσα στο ασανσέρ
          (assert (elevator is_at 1 has (+ ?A ?n) Adults and (+ ?M ?n2) Minors)
                  (floor 1 has 0 Adults and 0 Minors)
          )
          else
          (if (> ?n2 0) ;Έλεγχος για το αν υπάρχουν ανήλικα στον όροφο
              then
              (if (> ?A 0) ;Έλεγχος για το αν υπάρχουν ενήλικες στο ασανσέρ
                  then
                  (if (>= (- ?c (+ (+ ?A ?M) ?n2)) 0);Έλεγχος για το αν χωράνε όλα τα ανήλικα στο ασανσέρ
                      then
                      (bind ?free_space (- ?c (+ (+ ?A ?M) ?n2))) ; Κοιτάμε αν υπάρχει χώρος και για κάποιους ενήλικες
                      (if (> ?free_space 0)
                          then ;Αν ναι βάζουμε όλα τα παιδία και όσους ακόμα ενήλικες χωράνε
                          (assert (elevator is_at 1 has (+ ?A ?free_space) Adults and (+ ?M ?n2) Minors)
                                  (floor 1 has (- ?n ?free_space) Adults and 0 Minors)
                          )
                          else ;αλλιώς βάζουμε μόνο τα ανήλικα μεσα
                          (assert (elevator is_at 1 has ?A Adults and (+ ?M ?n2) Minors)
                                  (floor 1 has ?n Adults and 0 Minors)
                          )
                      )
                      else ; Αν δεν χωράνε όλα τα ανήλικα βάζουμε όσα χωράνε
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 1 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 1 has ?n Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                  )
                  else ;Αν δεν υπάρχουν ενήλικες στο ασανσέρ κοιτάμε αν υπάρχουν στον όροφο
                  (if (> ?n 0)
                      then ;Αν υπάρχουν βάζουμε έναν μέσα στο ασανσέρ
                      (bind ?free_space (- ?c (+ (+ ?A ?M) 1)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 1 has 1 Adults and (+ ?M ?free_space) Minors)
                                  (floor 1 has (- ?n 1) Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                      else ;Αλλιώς βάζουμε όσα παιδία χωράνε χωρίς ενήλικα
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 1 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 1 has ?n Adults and (- ?n2 ?free_space) Minors)
                          )
                     )
                  )
              )
              else ;Αν δεν έχουμε ανήλικα στον όροφο βλέπουμε αν χωράνε όλοι οι ενήλικες
              (if (>= (- ?c (+ (+ ?A ?M) ?n)) 0)
                  then
                  (assert (elevator is_at 1 has (+ ?A ?n) Adults and ?M Minors)
                          (floor 1 has 0 Adults and 0 Minors)
                  )
                  else
                  (bind ?free_space (- ?c (+ ?A ?M)))
                  (if (> ?free_space 0)
                      then
                      (assert (elevator is_at 1 has (+ ?A ?free_space) Adults and ?M Minors)
                              (floor 1 has (- ?n2 ?free_space) Adults and 0 Minors)
                      )
                  )
              )
          )
      )
)

(defrule enter_from_2
              "people at 2nd floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at 2 has ?A Adults and ?M Minors)
 ?j<- (floor 2 has ?n Adults and ?n2 Minors )
      (or (test (> ?n 0))
          (test (> ?n2 0))
      )
      (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ (+ ?A ?M) (+ ?n ?n2))) 0)
          then
          (assert (elevator is_at 2 has (+ ?A ?n) Adults and (+ ?M ?n2) Minors)
                  (floor 2 has 0 Adults and 0 Minors)
          )
          else
          (if (> ?n2 0)
              then
              (if (> ?A 0)
                  then
                  (if (>= (- ?c (+ (+ ?A ?M) ?n2)) 0)
                      then
                      (bind ?free_space (- ?c (+ (+ ?A ?M) ?n2)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 2 has (+ ?A ?free_space) Adults and (+ ?M ?n2) Minors)
                                  (floor 2 has (- ?n ?free_space) Adults and 0 Minors)
                          )
                          else
                          (assert (elevator is_at 2 has ?A Adults and (+ ?M ?n2) Minors)
                                  (floor 2 has ?n Adults and 0 Minors)
                          )
                      )
                      else
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 2 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 2 has ?n Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                  )
                  else
                  (if (> ?n 0)
                      then
                      (bind ?free_space (- ?c (+ (+ ?A ?M) 1)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 2 has 1 Adults and (+ ?M ?free_space) Minors)
                                  (floor 2 has (- ?n 1) Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                      else
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 2 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 2 has ?n  Adults and (- ?n2 ?free_space) Minors)
                          )
                     )
                  )
              )
              else
              (if (>= (- ?c (+ (+ ?A ?M) ?n)) 0)
                  then
                  (assert (elevator is_at 2 has (+ ?A ?n) Adults and ?M Minors)
                          (floor 2 has 0 Adults and 0 Minors)
                  )
                  else
                  (bind ?free_space (- ?c (+ ?A ?M)))
                  (if (> ?free_space 0)
                      then
                      (assert (elevator is_at 2 has (+ ?A ?free_space) Adults and ?M Minors)
                              (floor 2 has (- ?n2 ?free_space)  Adults and 0 Minors)
                      )
                  )
              )
          )
      )
)


(defrule enter_from_3
              "people at 3rd floor enter the elevator"
      (declare (salience 5))
 ?i<- (elevator is_at 3 has ?A Adults and ?M Minors)
 ?j<- (floor 3 has ?n Adults and ?n2 Minors )
      (or (test (> ?n 0))
          (test (> ?n2 0))
      )
      (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ (+ ?A ?M) (+ ?n ?n2))) 0)
          then
          (assert (elevator is_at 3 has (+ ?A ?n) Adults and (+ ?M ?n2) Minors)
                  (floor 3 has 0 Adults and 0 Minors)
          )
          else
          (if (> ?n2 0)
              then
              (if (> ?A 0)
                  then
                  (if (>= (- ?c (+ (+ ?A ?M) ?n2)) 0)
                      then
                      (bind ?free_space (- ?c (+ (+ ?A ?M) ?n2)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 3 has (+ ?A ?free_space) Adults and (+ ?M ?n2) Minors)
                                  (floor 3 has (- ?n ?free_space) Adults and 0 Minors)
                          )
                          else
                          (assert (elevator is_at 3 has ?A Adults and (+ ?M ?n2) Minors)
                                  (floor 3 has ?n Adults and 0 Minors)
                          )
                      )
                      else
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 3 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 3 has ?n Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                  )
                  else
                  (if (> ?n 0)
                      then
                      (bind ?free_space (- ?c (+ (+ ?A ?M) 1)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 3 has 1 Adults and (+ ?M ?free_space) Minors)
                                  (floor 3 has (- ?n 1) Adults and (- ?n2 ?free_space) Minors)
                          )
                      )
                      else
                      (bind ?free_space (- ?c (+ ?A ?M)))
                      (if (> ?free_space 0)
                          then
                          (assert (elevator is_at 3 has ?A Adults and (+ ?M ?free_space) Minors)
                                  (floor 3 has ?n Adults and (- ?n2 ?free_space) Minors)
                          )
                     )
                  )
              )
              else
              (if (>= (- ?c (+ (+ ?A ?M) ?n)) 0)
                  then
                  (assert (elevator is_at 3 has (+ ?A ?n) Adults and ?M Minors)
                          (floor 3 has 0 Adults and 0 Minors)
                  )
                  else
                  (bind ?free_space (- ?c (+ ?A ?M)))
                  (if (> ?free_space 0)
                      then
                      (assert (elevator is_at 3 has (+ ?A ?free_space) Adults and ?M Minors)
                              (floor 3 has (- ?n2 ?free_space)  Adults and 0 Minors)
                      )
                  )
              )
          )
      )
)
