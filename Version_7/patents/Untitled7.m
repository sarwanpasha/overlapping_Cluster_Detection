clear

Load_Data_File = 'Data.txt';
Load_Data_File_adj_matrix = 'edgeWeight.txt';

nData = load (Load_Data_File);
adj_matrix = load (Load_Data_File_adj_matrix);
adj_matrix=adj_matrix+1;
Node_Count = max(adj_matrix(:)); %Node Count
% edgeList = [1 2; 2 3; 2 4];
edgeList = adj_matrix;
 adj = sparse(edgeList(:, 1), edgeList(:, 2), 1, Node_Count, Node_Count);
adj2 = sparse(edgeList(:, 2), edgeList(:, 1), 1, Node_Count, Node_Count);
 
a2 = adj + adj2; % for both (1 2) and (2 1) edge
% adj = sparse(edgeList(:, 1), edgeList(:, 2), 1);
% aa=adjacency(adj);

A = adj;
sam = Summary(A);
% Node_Count = length(A); %Node Count
Max_Degree = max(sum(A)); % Max Degree
Min_Degree = min(sum(A)); % Max Degree
Average_Degree = (sum(sum(A))/2)/ Node_Count; % Average Degree
Edge_Count = sum(sum(a2))/2 ; % Edge Count

Node_Count
Max_Degree
Min_Degree
Average_Degree
Edge_Count
