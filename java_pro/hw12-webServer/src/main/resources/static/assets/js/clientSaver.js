function saveClient() {

    const clientNameBox = document.getElementById('clientNameTextBox');
    const clientPhoneBox = document.getElementById('clientPhoneTextBox');
    const clientAddressBox = document.getElementById('clientAddressTextBox');

    const clientName =  clientNameBox.value;
    const clientPhone = clientPhoneBox.value;
    const clientAddress = clientAddressBox.value;

    const data =
        {
            name: clientName,
            phone: clientPhone,
            address: clientAddress
        };

    fetch("api/client", {

        method: "POST",

        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },

        body: JSON.stringify(data)

    }).then(() => {
        window.location.reload();
    })
}