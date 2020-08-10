package com.ramadan.islamicAwareness.utils

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.WindowInsets
import com.ramadan.islamicAwareness.MainActivity

fun Context.startHomeActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

//
//fun Context.startRecordActivity() =
//    Intent(this, Record::class.java).also {
//        startActivity(it)
//    }
//
//fun Context.startWhiteboardsActivity() =
//    Intent(this, Whiteboards::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }
//
//fun Context.startWhiteboardActivity(whiteboard: Record) =
//    Intent(this, Record::class.java).also {
//        it.putExtra("path", whiteboard)
//        startActivity(it)
//    }
//
//fun Context.startNoteActivity(writtenNote: WrittenNote) =
//    Intent(this, Note::class.java).also {
//        it.putExtra("note", writtenNote)
//        startActivity(it)
//    }

