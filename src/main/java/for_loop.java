public class for_loop {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        int daysInMonth = 31;

        for (int day = 3; day <= 31; day += 3) {
            int dayOfWeek = (day - 1) % 7;

            String weekday = "";

            switch (dayOfWeek) {
                case 0:
                    weekday = "Monday";
                    break;

                case 1:
                    weekday = "Tuesday";
                case 2:
                    weekday = "Wednesday";
                    break;
                case 3:
                    weekday = "Thursday";
                    break;
                case 4:
                    weekday = "Friday";
                    break;
                case 5:
                    weekday = "Saturday";
                    break;
                case 6:
                    weekday = "Sunday";
                    break;

            }
            System.out.println("Day "+day+", "+ weekday);
        }

    }
}