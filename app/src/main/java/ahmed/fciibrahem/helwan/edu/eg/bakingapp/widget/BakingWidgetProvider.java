package ahmed.fciibrahem.helwan.edu.eg.bakingapp.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.MainActivity;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.data.RecipeProvider;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends android.appwidget.AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them (the user can add as many widget as he like)
        for (int appWidgetId : appWidgetIds)
        {
            //notice RemoteViews because ordinary views won't work
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);


            //create an Activity upon clicking on the wiget to go to the RecipeActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            //set a click listener when clicking on the widget to go to the RecipeActivity
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            //set the adapter for our widget
            views.setRemoteAdapter(R.id.lv_ingredients, new Intent(context, RecipeWidgetRemoteViewsService.class));


            PendingIntent clickPendingIntentTemplate = android.support.v4.app.TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.lv_ingredients, clickPendingIntentTemplate);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {

        super.onReceive(context, intent);
//if you recived an Intent in which has an ACTION to update the
        if (RecipeProvider.ACTION_DATA_UPDATED.equals(intent.getAction()))
        {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);
        }
    }
}

