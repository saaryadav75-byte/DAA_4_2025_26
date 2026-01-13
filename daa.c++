//Recurrence relation --> T(n) = 3T(n/2) + nlogn //
//asymptotic time complexity --> O(n^log2(3)) & The case of master theorem is, for a>b^k --> O(n^logb(a))//
#include <bits/stdc++.h>
using namespace std::chrono;
using namespace std;

void complexRec(int n) {

   if (n <= 2) {
       return;
   }

   int p = n;
   while (p > 0) {
       vector<int> temp(n);
       for (int i = 0; i < n; i++) {
           temp[i] = i ^ p;
       }
       p >>= 1;
    }

   vector<int> small(n);
   for (int i = 0; i < n; i++) {
       small[i] = i * i;
   }

   if (n % 3 == 0) {
       reverse(small.begin(), small.end());
   } else {
       reverse(small.begin(), small.end());
   }

   complexRec(n / 2);
   complexRec(n / 2);
   complexRec(n / 2);

int main()
   auto start = high_resolution_clock:;now();
   complexRec(3000);

   auto end = high_resolution_clock:;now();
   auto duration = duration_cast < milliseconds > (end-start);

   cout<< duration.count()<<"ms";
   return 0;
}