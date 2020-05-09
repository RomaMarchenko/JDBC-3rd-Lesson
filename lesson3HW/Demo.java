package lesson3HW;


import exceptions.BadRequestException;

public class Demo {
    public static void main(String[] args) throws BadRequestException {
        Solution solution = new Solution();

        //System.out.println(solution.findProductsByPrice(1, 0));

        //System.out.println(solution.findProductsByPrice(50, 50));

        //System.out.println(solution.findProductsByName("sdgsgsg"));

        //System.out.println(solution.findProductsByName("sdgs gsg"));

        //System.out.println(solution.findProductsByName("sdgs-"));

        //System.out.println(solution.findProductsByName("sd"));

        //System.out.println(solution.findProductsByName("toy"));

        System.out.println(solution.findProductsWithEmptyDescription());
    }
}
