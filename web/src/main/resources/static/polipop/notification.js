let polipop = new Polipop('mypolipop', {
    layout: 'popups',
    insert: 'before',
    pool: 5,
    sticky: false,
    life: 2000
});

function showNotification() {
    polipop.add({
        content: 'This is the message content.',
        title: 'Message',
        type: 'success',
    });

}
