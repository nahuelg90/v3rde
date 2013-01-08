
package core.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class PopupHandler {

	private static PopupHandler instance = new PopupHandler();

	private ConnectDelegate _connectDelegate;
	private ConfirmDelegate _confirmDelegate;
	private AlertDelegate	_alertDelegate;


	private PopupHandler() {}

	public static PopupHandler getInstance(){
		return instance;
	}

	/**
	 * Muestra un Connection Popup con botones de Reintentar y Cancelar.
	 *
	 * @param context
	 * @param connection delegate
	 * @return la instancia del popup
	 */
    public AlertDialog showConnectionPopUp(Context context, ConnectDelegate delegate){
    	
    	_connectDelegate = delegate;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Error de Conexión!");
        builder.setMessage("Chequee su conexión a internet y vuelva a intentarlo.");
        builder.setCancelable(false);

        builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int id) {
        		_connectDelegate.onRetry();
        	}
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int id) {
        		_connectDelegate.onCancel();
        	}
        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }


    /**
     * Muestra un Alert Popup.
     *
     * @param title
     * @param text
     * @param context
     * @param delegate alert delegate
     * @return la instancia del popup
     */
    public AlertDialog showAlertPopUp(String title, String text, Context context, AlertDelegate delegate){

    	_alertDelegate = delegate;

    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(text);

        builder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	_alertDelegate.onOk();
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    /**
     * Muestra un Alert Popup sin titulo.
     *
     * @param text
     * @param context
     * @param delegate alert delegate
     * @return la instancia del popup
     */
    public AlertDialog showAlertPopUp(String text, Context context, AlertDelegate delegate){
    	
    	_alertDelegate = delegate;
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	builder.setMessage(text);
    	
    	builder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		
    		public void onClick(DialogInterface dialog, int id) {
    			_alertDelegate.onOk();
    		}
    		
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    	return alert;
    }

    /**
     * Muestra un Confirmation Popup con botones de Ok y Cancelar.
     * @param text
     * @param contexto
     * @param delegate confirm delegate
     * @return la instancia del popup
     */
    public AlertDialog popupWithText(String text, Context contexto, ConfirmDelegate delegate){

    	_confirmDelegate = delegate;

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setMessage(text).setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	_confirmDelegate.onYes();
            }

        });

        builder.setNegativeButton("Cancelar",  new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	_confirmDelegate.onNo();
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
		return alert;
    }
}
