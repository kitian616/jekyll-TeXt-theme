public class Inform4 {
    static int nSize = 4;
    private static int[] arr;

    public static void main(String[] args) {
        int[] arr = new int[nSize];
        makeArray(arr);

        for(int i=0;i<nSize;i++){
            System.out.println(arr[i]+" ");
        }
    }
    public static void makeArray(int[] ar){
        for (int i =0; i<nSize;i++){
            arr[i] = i;
        }
    }
}
