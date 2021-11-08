# -*- coding: utf-8 -*-
"""
Created on Sun May 31 22:14:55 2020

@author: theod
"""
import pandas as pd
from pm4py.objects.log.importer.xes import importer as xes_import
from pm4py.objects.log.util import log as utils
from pm4py.statistics.start_activities.log.get import get_start_activities
from pm4py.statistics.end_activities.log.get import get_end_activities
from pm4py.algo.filtering.log.end_activities import end_activities_filter
from pm4py.visualization.petrinet import factory as vis_factory
from pm4py.algo.discovery.alpha import factory as alpha_miner
from pm4py.algo.discovery.heuristics import factory as heuristics_miner
from pm4py.algo.discovery.inductive import factory as inductive_miner
from pm4py.evaluation import factory as evaluation_factory
from pm4py.algo.conformance.tokenreplay import factory as token_replay

# Συνάρτηση που καλείται για κάθε αλγόριθμό και για τα δυο logs για να δούμε πόσα traces δεν συνάδουν
# με το process model 
def print_fit_traces(log, net, initial_marking, final_marking):
    replayed_traces = token_replay.apply(log, net, initial_marking, final_marking) 
    fit_traces = 0
    for trace in replayed_traces:
        if not trace["trace_is_fit"]:
            fit_traces += 1
    print("Number of non fit traces : ", fit_traces)

# 1. Διαβάζει το event log
log = xes_import.apply('edited_hh110_labour.xes')

trace_key_list = []
event_key_list = []

event_count = 0 # Counter για να μετρήσουμε το πλήθος των event
for trace in log:
    # Βρίσκουμε τα keys κάθε trace και αν δεν υπάρχουν ήδη στη λίστα με τα key
    # δηλαδή την trace_key_list τα προσθέτουμε στη λίστα. 
    for trace_key in trace.attributes.keys():
        if trace_key not in trace_key_list:
            trace_key_list.append(trace_key)
    for event in trace:
        # Κάνουμε το ίδιο και για τα keys των events
        for event_key in event.keys():
            if event_key not in event_key_list:
                event_key_list.append(event_key)
        event_count += 1 # Κάθε φορά που μπαίνουμε στην for των events αυξάνουμε τον counter κατά 1

# 2. Εμφανίζει τη δομή του trace και του event
print("Trace keys : " + str(trace_key_list))
print("Event keys : " + str(event_key_list))

# 3. Εμφανίζει το πλήθος των traces
print("Number of traces : " + str(len(log)))

# 4. Εμφανίζει το πλήθος των events
print("Number of events : " + str(event_count))

# 5. Εμφανίζει τα διαφορετικά events από τα οποία αποτελείται το event log
unique_events = utils.get_event_labels(log,'concept:name')
print("Events of log : " + str(unique_events))

# 6. Εμφανίζει τις δραστηριότητες με τις οποίες αρχίζουν και τελειώνουν τα
# traces και τη συχνότητα εμφάνισής τους

# Πρώτα βρίσκουμε τις δραστηριότητες με τις οποίες αρχίζουν
start_activities = get_start_activities(log)
print("Starting activities: " + str(start_activities))

# Και τώρα αντίστοιχα το ίδιο για τις δραστηριότητες με τις οποίες τελειώνουν
# τα traces
end_activities = get_end_activities(log)
print("End activities" + str(end_activities))

# 7. Εμφανίζει σε πίνακα το case id, activity name, transition (start ή
# complete), timestamp
# Φτιάχνουμε ένα άδειο DataFrame
log_df = pd.DataFrame(columns = ["Case ID" , "Activity Name" , "Transition" , "Timestamp"])
for trace_id, trace in enumerate(log):
    for event_index, event in enumerate(trace):
        #Φτιάχνουμε ένα DataFrame στο οποίο ουσιαστικά φορτώνουμε τα στοιχεία
        #που θέλουμε από το τρέχον event, δηλαδή μια γραμμή του πίνακα
        #που σκοπεύουμε να δημιουργήσουμε
        row = pd.DataFrame({
            "Case ID" : [trace.attributes["concept:name"]],
            "Activity Name" : [event["concept:name"]],
            "Transition" : [event["lifecycle:transition"]],
            "Timestamp" : [event["time:timestamp"]]
            })
        #Κάνουμε append την γραμμή που φτιάξαμε στο DataFrame που ορίσαμε έξω από την
        #επανάληψη
        log_df = log_df.append(row, ignore_index = True)
print("Printing log table : \n")
print(log_df)
#Αν θέλουμε να εμφανίσουμε όλο το dataframe στην κονσόλα 
# βγάζουμε από το σχόλιο την παρακάτω εντολή
#print(log_df.to_string(index=False))

#Για καλύτερη ανάγνωση εξάγουμε το log_df ως csv
log_df.to_csv('log_table.csv', index = False)

# 8. Φιλτράρει το event log και θα κρατήσει μόνο τα traces που τελειώνουν
# με την δραστηριότητα "end"

filtered_log = end_activities_filter.apply(log,["End"])
print("New log : \n " + str(filtered_log))
# Για επαλήθευση τυπώνουμε το size του filtered_log θα πρέπει να είναι 
# ίσο με τη συχνότητα εμφάνισης του "End" 
print("Size of filtered log : " + str(len(filtered_log)))

# Για καλύτερη ανάγνωση-επαλήθευση αν θέλουμε
# εξάγουμε το filtered_log ως csv
# βγάζοντας τις 2 επόμενες εντολές από τα comments
#filt_log_df = pd.DataFrame(filtered_log)
#filt_log_df.to_csv('filtered_log.csv')

# 9. Μοντέλα διεργασιών

# Alpha Miner
# Για το αρχικό log
net, initial_marking, final_marking = alpha_miner.apply(log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('alpha_miner_log_evaluation.csv')


# Για το filtered log
net, initial_marking, final_marking = alpha_miner.apply(filtered_log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(filtered_log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('alpha_miner_filtered_log_evaluation.csv')

# Heuristics Miner
# Για το αρχικό log
net, initial_marking, final_marking =  heuristics_miner.apply(log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('heuristic_miner_log_evaluation.csv')

#alignments = alignment.apply_log(log, net, initial_marking, final_marking)
#pretty_print_alignments(alignments)

# Για το filtered log
net, initial_marking, final_marking =  heuristics_miner.apply(filtered_log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(filtered_log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('heuristic_miner_filtered_log_evaluation.csv')

# Inductive Miner
# Για το αρχικό log
net, initial_marking, final_marking =  inductive_miner.apply(log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('inductive_miner_log_evaluation.csv')

# Για το filtered log
net, initial_marking, final_marking =  inductive_miner.apply(filtered_log)
gviz = vis_factory.apply(net, initial_marking, final_marking)
vis_factory.view(gviz)
evaluation_result = evaluation_factory.apply(filtered_log, net, initial_marking,final_marking)
print(evaluation_result)
print_fit_traces(log, net, initial_marking, final_marking)
#evaluation_df = pd.DataFrame(evaluation_result)
#print(evaluation_df)
#evaluation_df.to_csv('inductive_miner_filtered_log_evaluation.csv')


