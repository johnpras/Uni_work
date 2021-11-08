txt = open("dfa.txt", "r")        #ανοίγω και διαβάζω το αρχείο 
content = []                 #δημιουργία λιστας
for line in txt:                       
    content.append(line.rstrip("\n"))   #βάζουμε το περιεχόμενο του αρχείου στην λίστα και αφαιρούμε τον χαρακτήρα αλλαγής γραμμής στο τέλος κάθε γραμμής
txt.close()

#συνάρτηση μετάβασης, ελέγχω bit
def transition(current_state,bit,transition_table,number_of_states):
    for i in range(0,number_of_states*len(alphabet)):
        if (transition_table[i][0] == current_state) and (transition_table[i][1] == bit):
            return transition_table[i][2]


#παρακάτω δηλώνουμε τις μεταβλητές μας
number_of_states = int(content[0])      #στην πρώτη γραμμή της λίστας μας έχουμε τις καταστάσεις μας, το κάνουμε intiger  
alphabet=content[1].replace(" ","")     #στην δεύτερη γραμμή της λίστας μας έχουμε το αλφάβητο, αφαιρούμε τα κενά από την λίστα του αλφαβήτου
alphabet_to_display_in_menu=content[1]
initial_state=content[2]                #στην τρίτη γραμμή της λίστας μας έχουμε την αρχική κατάσταση
final_state=content[3]                  #στην τέταρτη γραμμή της λίστας μας έχουμε την τελική κατάσταση
transition_table = []                   #δημιουργία λίστας με όνομα πίνακας μετάβασης
transition_table = content[4:]          #γέμισμα πίνακα μετάβασης

for i in range(0,len(transition_table)):
    transition_table[i] = transition_table[i].replace(" ","")   #αφαιρούμε τα κενά του πίνακα μετάβασης

current_state = initial_state   #αρχικοποιούμε την τρέχουσα κατάσταση

#δήλωση μεταβλητών που θα χρειαστούμε στην while
choice = True
yes_choice = ['y']
no_choice = ['n']
#δημιουργία επανάληψης για την αίσθηση menu, ώστε να ρωτάει τον χρήστη αν θέλει να δώσει κι' άλλη λέξη ή να σταματήσει
while choice:
    choice = input("Do you want to give an input? (y/n) ").lower()
    if choice in yes_choice:
        USERinput = input("Start typing...: "+"(hint: the alphabet is " +alphabet_to_display_in_menu+")\n" )

        #για κάθε bit του input του χρήστη ελέγχουμε με την συνάρτηση μετάβασης
        for bit in USERinput:
            current_state = transition(current_state,bit,transition_table,number_of_states)

        #εμφανίζει μήνυμα ανάλογα με το αν είμαστε σε τελική κατάσταση ή οχι
        if current_state in final_state:
            print("accepted")
        else:
            print("rejected")

    elif choice in no_choice:
        choice = False
    else:
        print("Invalid input. \nTry again...")
        choice = True