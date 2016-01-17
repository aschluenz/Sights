package AppLogic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import sights.sights.R;

/**
 * Created by AndySchluenz on 17.01.16.
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message, Boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        if(status !=null)

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
