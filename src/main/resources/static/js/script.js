
let connectionInternetType = document.getElementById('connection-internet-type');
if (connectionInternetType !== null) {
    let configId = document.getElementById('config-form').dataset.configId;
    connectionInternetType.addEventListener('change', function(e){
        let typeId = e.target.selectedOptions[0].dataset.typeId;
        window.location.href = `/config/${configId}/connection-internet-type/${typeId}`;
    });
}

let devices = document.getElementById('devices');
if (devices !== null) {
    let generalConfigId = devices.dataset.generalConfigId;
    let deviceButtons = document.querySelectorAll('.deleteDevice');
    deviceButtons.forEach(deviceButton => {
        deviceButton.addEventListener('click', function(e){
            let deviceId = e.target.dataset.deviceId;
            window.location.href = `/devices/${generalConfigId}/delete/${deviceId}`;
        });
    });

    let addNewDeviceButton = document.getElementById('add-new-device');
    addNewDeviceButton.addEventListener('click', function(e){
        window.location.href = `/devices/${generalConfigId}/generate`;
    });
}
