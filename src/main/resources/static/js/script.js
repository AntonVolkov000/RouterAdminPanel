
let connectionInternetType = document.getElementById('connection-internet-type');
if (connectionInternetType !== null) {
    let configId = document.getElementById('config-form').dataset.configId;
    connectionInternetType.addEventListener('change', function(e){
        let typeId = e.target.selectedOptions[0].dataset.typeId;
        window.location.href = `/config/${configId}/connection-internet-type/${typeId}`;
    });
}
