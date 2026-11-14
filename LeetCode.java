public class Leetcode{

    public long countSubarrays(int[] nums, int minK, int maxK) {
        int n = nums.length;

        int sum = 0, cnt =0;

        for(int i=0; i<n-1; ++i){
            if(nums[i] >= minK && nums[i] <= maxK){
                ++cnt;
            } else {
                int comb = (cnt * (cnt+1)) / 2; 
                sum += comb;
                cnt = 0;
            }
        }

        int comb = (cnt * (cnt+1)) / 2; 
        sum += comb;
        
        return sum;
    }

    public static void main(String[] args){

        
    }
}