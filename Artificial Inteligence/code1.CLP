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
    (elevator is_at 0 has 0) ; �� ������� ����� ��� ������� �� 0 ��������
    ; ���� �� ������ ����� ������
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
 ?i<- (elevator is_at ? has 5) ;�� ������� ����� �� ������� ����� ������
   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0)));�� ������� ����� ��� ������� ��� �����


(defrule go_to_base_B      "no_more people to take"
      (declare (salience 8))
 ?i<- (elevator is_at ? has ?y);�� ������� ����� �� ������� ����� �� y ������ ��������
      (test (> ?y 0) )
      ;���� �� ������ ����� ������
      (floor 1 has 0)
      (floor 2 has 0)
      (floor 3 has 0)

   =>
      (retract ?i)
      (assert (elevator is_at 0 has 0)));�� ������� ����� ��� ������� ��� �����

(defrule move_to_1          "elevator moves to 1st floor "
 ?i<- (elevator is_at ?x has ?y);�� ������� ����� ���� � ����� �� y ������ ��������
      (test (not (= ?x 1)));���������� �� ��� ����� ��� 1� ����� ���
      (floor 1 has ?n)
       (test (not (= ?n 0)));���������� �� ��� ����� ������ � ������
   =>
      (retract ?i)
      (assert (elevator is_at 1 has ?y)));�� ������� ���� ���� 1� ����� �� y ������ ��������

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
 ?i<- (elevator is_at 1 has ?p);�� ������� ����� ���� 1� ����� ��� ���� p ������ ��������
 ?j<- (floor 1 has ?r);� ������ ������ ���� r ���������
      (test (> ?r 0));���������� �� r �� ����� ���������� ��� 0
        (capacity ?c)
   =>
      (retract ?i ?j)
      (if (>= (- ?c (+ ?p ?r)) 0)
          then
          (assert (elevator is_at 1 has (+ ?p ?r));�� ������� ����� ��� 1� ����� �� p+r ��������
                  (floor 1 has 0));� ������ ������ ����� ������
          else
          (assert (elevator is_at 1 has ?c);�� ������� ����� ��� 1� ����� �� c ��������
                  (floor 1 has (- ?r (- ?c  ?p))))));� ������ ������ ���� c-(p+r) ��������

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
