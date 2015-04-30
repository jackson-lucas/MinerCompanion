package devbox.com.br.minercompanion.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import devbox.com.br.minercompanion.R;

public class ProfileListAdapter extends BaseAdapter  {


    private List<String> listStrings;
    private List<String> listTime;
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy\n hh:mm:ss");

    //Classe utilizada para instanciar os objetos do XML
    private LayoutInflater inflater;

    private int darkGreen;

    public ProfileListAdapter(Context context, List<String> listStrings) {
        this.listStrings = listStrings;
        listTime = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       darkGreen = context.getResources().getColor(R.color.darkGreen);
    }

    public void addItem(final String item) {
        this.listStrings.add(item);

        Date date = new java.util.Date();

        this.listTime.add(dateFormat.format(date).toString());

        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listStrings.size();
    }

    @Override
    public Object getItem(int position) {
        return listStrings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //Pega o registro da lista e trasnfere para o objeto estadoVO
        String string = listStrings.get(position);

        //Utiliza o XML estado_row para apresentar na tela
        convertView = inflater.inflate(R.layout.log_row, null);

        //Instância os objetos do XML
        TextView textView = (TextView) convertView.findViewById(R.id.textRow);
        TextView textTime = (TextView) convertView.findViewById(R.id.textTime);

        textTime.setText(listTime.get(position));

        textView.setText(string);
        textView.setTextColor(darkGreen);

        return convertView;
    }
}
