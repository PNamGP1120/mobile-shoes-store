# Backend Media Setup - mobile-shoes-store

## Firebase
- Authentication: Enabled
- Firestore: Enabled

## Cloudinary
- Used instead of Firebase Storage
- cloud_name: <your_cloud_name>
- upload_preset: <your_unsigned_preset>

## Notes
- Do not store api_secret in Android app
- Product, category, and avatar images will be uploaded to Cloudinary
- Firestore will only store image URLs/public IDs