package io.mosip.registration.tpm.asymmetric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.registration.config.AppConfig;
import io.mosip.registration.constants.LoggerConstants;
import io.mosip.registration.constants.RegistrationConstants;

import tss.Tpm;
import tss.tpm.TPMS_NULL_ASYM_SCHEME;

/**
 * Class for encrypting the data using asymmetric cryto-alogirthm in TPM
 * 
 * @author Balaji Sridharan
 * @since 1.0.0
 */
@Service
public class AsymmetricEncryptionService {

	private static final Logger LOGGER = AppConfig.getLogger(AsymmetricEncryptionService.class);
	@Autowired
	private AsymmetricKeyCreationService asymmetricKeyCreationService;

	/**
	 * Encrypts the input data using the TPM
	 * 
	 * @param tpm
	 *            the instance of {@link Tpm}
	 * @param dataToEncrypt
	 *            the data to be encrypted
	 * @return returns the TPM encrypted data
	 */
	public byte[] encryptUsingTPM(Tpm tpm, byte[] dataToEncrypt) {
		LOGGER.info(LoggerConstants.TPM_ASYM_ENCRYPTION, RegistrationConstants.APPLICATION_NAME,
				RegistrationConstants.APPLICATION_ID, "Encrypting the data by asymmetric algorithm using TPM");

		return tpm.RSA_Encrypt(asymmetricKeyCreationService.createPersistentKey(tpm), dataToEncrypt,
				new TPMS_NULL_ASYM_SCHEME(), RegistrationConstants.NULL_VECTOR);
	}

}