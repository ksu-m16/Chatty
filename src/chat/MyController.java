package chat;

import java.io.IOException;

public class MyController implements IController {
	
	private IModel model;
	
	public IModel getModel() {
		return model;
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	@Override
	public boolean checkValue(String s) {
		try{
		Integer.parseInt(s);
		}
		catch (NumberFormatException e){
			return false;
		}
		return true;
		
	}

	@Override
	public void setValue(String s) {
		if(checkValue(s)){
			try {
				model.setNumber(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getValue() {
		try {
			return String.valueOf(model.getNumber());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Unable to open file";
		}
	}

	
}
