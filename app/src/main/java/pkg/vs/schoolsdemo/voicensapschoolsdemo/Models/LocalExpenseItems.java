package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

/**
 * Created by devi on 5/27/2019.
 */

public class LocalExpenseItems {
    String BoardLodge;
    String ConvTravel;
    String Food;
    String Fuel;
    String PostageCourier;
    String Printing;
    String Telephone;
    String Misc;


public LocalExpenseItems(String board, String ConveyTravel, String FoodBeverage, String FuelPetrol, String postage, String printinig, String Telephone, String Miscellaneous){

      this.BoardLodge=board;
      this.ConvTravel=ConveyTravel;
      this.Food=FoodBeverage;
      this.Fuel=FuelPetrol;
      this.PostageCourier=postage;
      this.Printing=printinig;
      this.Telephone=Telephone;
      this.Misc=Miscellaneous;
}
    public String getBoardLodge(){
        return BoardLodge;
    }
    public void setBoardLodge(String boardLodge){
        this.BoardLodge=boardLodge;
    }
    public String getConvTravel(){
        return ConvTravel;
    }
    public void setConvTravel(String convey){
        this.ConvTravel=convey;
    }
    public String getFood(){
        return Food;
    }
    public void setFood(String food){
        this.Food=food;
    }
    public String getFuel(){
        return Fuel;
    }
    public void setFuel(String fuel){
        this.Fuel=fuel;
    }
    public String getPostageCourier(){
        return PostageCourier;
    }
    public void setPostageCourier(String post){
        this.PostageCourier=post;
    }
    public String getPrinting(){
        return Printing;
    }
    public void setPrinting(String printing){
        this.Printing=printing;
    }
    public String getTelephone(){
        return Telephone;
    }
    public void setTelephone(String Telephone){
        this.Telephone=Telephone;
    }
    public String getMisc(){
        return Misc;
    }
    public void setMisc(String miscell){
        this.Misc =miscell;
    }



}
