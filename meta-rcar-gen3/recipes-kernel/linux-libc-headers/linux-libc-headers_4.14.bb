require recipes-kernel/linux-libc-headers/linux-libc-headers.inc
require include/iccom-control.inc
require include/adsp-control.inc

RENESAS_BSP_URL = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/horms/renesas-bsp.git"
BRANCH = "v4.14.75-ltsi/rcar-3.9.2"
SRCREV = "a5266d298124874c2c06b8b13d073f6ecc2ee355"

SRC_URI = "${RENESAS_BSP_URL};branch=${BRANCH}"

# Enable RPMSG_VIRTIO depend on ICCOM
SRC_URI_append = " \
    ${@oe.utils.conditional("USE_ICCOM", "1", " file://0001-rpmsg-Add-message-to-be-able-to-configure-RPMSG_VIRT.patch", "", d)} \
"

# Add ADSP ALSA driver
SUPPORT_ADSP_ASOC = " \
    file://0001-ADSP-add-document-for-compatible-string-renesas-rcar.patch \
    file://0002-ADSP-add-ADSP-sound-driver-source.patch \
    file://0003-ADSP-add-build-for-ADSP-sound-driver.patch \
    file://0004-ADSP-integrate-ADSP-sound-for-H3-M3-M3N-board.patch \
    file://0005-ADSP-integrate-ADSP-sound-for-E3-board.patch \
    file://0006-ADSP-remove-HDMI-support-from-rcar-sound.patch \
"

SRC_URI_append = " \
    ${@oe.utils.conditional("USE_ADSP", "1", "${SUPPORT_ADSP_ASOC}", "", d)} \
"

S = "${WORKDIR}/git"

# W/A Fix build issue with Linux v4.14
SRC_URI_append = " \
    file://0001-arm64-bpf-correct-broken-uapi-for-BPF_PROG_TYPE_PERF.patch \
"
