const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendInfoNotification = functions.firestore
    .document('/root/ar/collection/information/information/righteous caliphs')
    .onUpdate(async (change, context) => {
        const newValue = change.after.data();
        var json = JSON.stringify(newValue.content);
        var payload = {
            notification: {
                title: `📌 ${newValue.title}`,
                body: "نشط ذاكرتك 🧠، في دقائق محدودة",
                image: newValue.image,
                click_action: "info",
                sound: "default",
                // content_available: true,
                // show_in_foreground: true
            },
            data: {
                id: change.after.id,
                title: newValue.title,
            }
        };
        await admin.messaging().sendToTopic("allUsers", payload);
    });

exports.sendVideoNotification = functions.firestore
    .document('/root/ar/collection/videos/videos/{newVideo}')
    .onCreate(async (snap, context) => {
        const newValue = snap.after.data();
        var json = JSON.stringify(newValue.id);
        var payload = {
            notification: {
                title: `📌 ${newValue.title}`,
                body: "نشط ذاكرتك 🧠، في دقائق محدودة",
                image: newValue.image,
                click_action: "video",
                sound: "default",
            },
            data: {
                id: snap.after.id,
                title: newValue.title,
                videosId: json,
            }
        };
        await admin.messaging().sendToTopic("allUsers", payload);
    });

exports.sendRamadanNotification = functions.firestore
    .document('/root/ar/collection/videos/videos/{newVideo}')
    .onCreate(async (snap, context) => {
        const newValue = snap.after.data();
        var json = JSON.stringify(newValue.id);
        var payload = {
            notification: {
                title: `📌 ${newValue.title}`,
                body: "نشط ذاكرتك 🧠، في دقائق محدودة",
                image: newValue.image,
                click_action: "info",
                sound: "default",
            },
            data: {
                id: snap.after.id,
                title: newValue.title,
                videosId: json,
            }
        };
        await admin.messaging().sendToTopic("allUsers", payload);
    });
