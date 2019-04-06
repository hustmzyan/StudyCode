#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>
using namespace std;

static int bucket_size[3] = {8,5,3};
int step[80];


struct action{
	int from;
	int to;
	int water;
};

struct bucket_states{
	int bucket_state[3];
	action ac;
	bucket_states(int a, int b, int c, int from, int to, int water){
		bucket_state[0] = a;
		bucket_state[1] = b;
		bucket_state[2] = c;
		ac.from = from;
		ac.to = to;
		ac.water = water;
	}
	bucket_states()
	{
	}

	//倒水动作
	void set_action(int dump_water, int from, int to) {
		ac.from = from;
		ac.to = to;
		ac.water = dump_water;
	}

	//状态判断
	bool is_empty(int bucket_idx) {
		if(bucket_idx > 2) {
			return false;
		}
		return (bucket_state[bucket_idx] == 0);
	}
	bool is_full(int bucket_idx) {
		return (bucket_state[bucket_idx] >= bucket_size[bucket_idx]);
	}
	bool is_final() {
		return (bucket_state[0] == 4 && bucket_state[1] == 4);
	}


	bool dump_water(int from, int to, bucket_states& next){
		int bucket_water[3];
		for(int i = 0; i < 3; i++)
			bucket_water[i] = bucket_state[i];
		int dump_water = bucket_size[to] - bucket_state[to];
		//如果from中的水大于to桶剩余的空间，则倒出dunp_water的水量
		if(bucket_water[from] >= dump_water) {
			bucket_water[to] += dump_water;
			bucket_water[from] -= dump_water;
		} else {//否则倒出from桶中的所有水
			dump_water = bucket_water[from];
			bucket_water[to] += dump_water;
			bucket_water[from] = 0;
		}
		if(dump_water > 0){
			for(int i = 0; i < 3; i++)
				next.bucket_state[i] = bucket_water[i];
			next.set_action(dump_water, from, to);
			return true;
		}
		return false;
	}
};

bool is_action_valid(bucket_states& cur, int from, int to){
	if((from != to) && !cur.is_empty(from) && !cur.is_full(to))
		return true;
	return false;
}

bool is_loop(vector<bucket_states>& states, bucket_states& next) {
	int i = 0;
	for(; i < states.size(); ++i) {
		if(states[i].bucket_state[0] == next.bucket_state[0] &&
			states[i].bucket_state[1] == next.bucket_state[1] &&
			states[i].bucket_state[2] == next.bucket_state[2])
			return true;
	}
	return false;
}


void print_states(bucket_states& bucket){
	cout << "bucket_states : " << bucket.bucket_state[0] << " " << 
		bucket.bucket_state[1] << " " << bucket.bucket_state[2] << 
		"  from " << bucket.ac.from << " to " << bucket.ac.to << 
		"  dump " << bucket.ac.water << "L water." << endl; 
}


void DFS(vector<bucket_states>& states, int& cnt, int& shortest)
{
	bucket_states cur = states.back();
	if(cur.is_final()){
		++cnt;
		shortest = min(shortest, (int)states.size());
		for_each(states.begin(), states.end(), print_states);
		cout<<"-----------------------------------------------------"<<endl;
		return;
	}
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			if(is_action_valid(cur, i, j))
			{
				bucket_states next_state;
				bool is_dump = cur.dump_water(i, j, next_state);
				if(is_dump && !is_loop(states, next_state)){
					states.push_back(next_state);
					DFS(states, cnt, shortest);
					states.pop_back();
				}
			}
		}
	}
}

void BFS(queue<bucket_states>& states, vector<bucket_states>& path, vector<bucket_states>& real_path, int& cnt, int& shortest){
	int step_idx = 1, root_idx = -1;
	while(states.size())
	{
		//cout<<states.size()<<endl;
		//cout<<"while"<<endl;
		bucket_states cur = states.front();
		states.pop();
		root_idx++;
		if(cur.is_final()){
			int i = root_idx;
			real_path.clear();
			while(step[i] != -1)
			{
				real_path.push_back(path[i]);
				i = step[i];
				if(i==0)
					real_path.push_back(path[0]);
			}
			++cnt;
			shortest = min(shortest, (int)real_path.size());
			reverse(real_path.begin(), real_path.end());
			for_each(real_path.begin(), real_path.end(), print_states);
			cout<<"-------------------------------------------"<<endl;
			//step_idx++;
			//cout<<"continue"<<endl;
			continue;
			
		}
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				if(is_action_valid(cur, i, j))
				{
					bucket_states next_state;
					bool is_dump = cur.dump_water(i, j, next_state);
					if(is_dump && !is_loop(path, next_state)){
						path.push_back(next_state);
						step[step_idx++] = root_idx;
						states.push(next_state);
					}
				}
			}
	}
}




void main()
{
	//设置初始状态
	//vector<bucket_states> states;
	bucket_states start(8, 0, 0, -1, 0, 8);
	//states.push_back(start);

	queue<bucket_states> states;
	vector<bucket_states> path;
	vector<bucket_states> real_path;
	
	states.push(start);
	path.push_back(start);
	step[0] = -1;


	int cnt = 0, shortest = INT_MAX;

	//DFS(states, cnt, shortest);
	BFS(states, path, real_path, cnt, shortest);

	cout << "result size : " << cnt << " shortest : " << shortest <<endl;
	system("pause");
	return;
}
