package br.senac.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import br.senac.noteapp.R
import br.senac.noteapp.databinding.NoteCardBinding
import br.senac.noteapp.model.Note


class NoteRecyclerViewAdapter(private val noteList: List<Note> ) : RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() {

    class NoteViewHolder(private val binding: NoteCardBinding, private val color: Int): RecyclerView.ViewHolder(binding.root) {
        fun bind(note : Note) {
            binding.txtTitle.text = note.title
            binding.txtDesc.text = note.desc
            binding.txtUser.text = note.user
            binding.root.setCardBackgroundColor(color)
        }
    }

//    override fun getItemCount(): Int {
//        return noteList.size
//    }
//    ou
    override fun getItemCount() = noteList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteCardBinding.inflate(layoutInflater, parent, false)

        val prefManager = PreferenceManager.getDefaultSharedPreferences(parent.context)
        val color = prefManager.getInt("noteColor", R.color.noteDefaultColor)

        return NoteViewHolder(binding, color)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }
}