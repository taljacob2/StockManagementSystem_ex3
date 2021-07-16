let polipop = new Polipop('mypolipop', {
    layout: 'popups',
    insert: 'before',
    pool: 5,
    sticky: false,
    life: 2000
});

function showSuccessNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'success',
    });
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