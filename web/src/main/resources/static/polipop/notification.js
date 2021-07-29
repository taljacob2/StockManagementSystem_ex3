let polipop = new Polipop('mypolipop', {
    layout: 'popups',
    insert: 'before',
    pool: 0,
    sticky: false,
    life: 2000,
    progressbar: true,
    pauseOnHover: true
});

function showNotification(content, title, type) {
    polipop.add({
        content,
        title,
        type
    });
}

function showSuccessNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'success',
    });

    // let queue = polipop.queue;
    // queue.forEach(element => console.log(element));

    // let elements = polipop.elements;
    // elements.forEach(element => console.log(element));

}

function showErrorNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'error',
    });
}

function showInfoNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'info',
    });
}

function showDefaultNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'default',
    });
}

function showWarningNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'warning',
    });
}